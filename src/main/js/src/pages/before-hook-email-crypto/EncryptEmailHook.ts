// EncryptEmailHook.ts
import type { Hook, HookContext, EvaluationDetails, FlagValue } from "@openfeature/web-sdk";
import { CachedLogger } from "./CachedLogger";
import EmailHasher from "./EmailHasher";

const logger = new CachedLogger();

export class EncryptEmailHook implements Hook {
    before(hookContext: HookContext) {
        const emailHashPromise = EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string);

        emailHashPromise.then(hashedEmail => {
            logger.debug(
                `[Before Hook] Snapshot before processing.`,
                hookContext.flagKey,
                null,
                hookContext,
                null
            );

            // 由於這裡的 context 是 readonly 的，無法做替換 userEmailInput 的動作。
            // 僅在log中顯示 Hashed Email。

            if (hookContext.context['shouldBeforeHookFailed'] as boolean) {
                // 模擬 Before Hook 的自定義前處理(Hash Email)失敗。
                const errorMessage = `[Before Hook] Hasher Failed.`;
                logger.error(
                    errorMessage,
                    hookContext.flagKey,
                    null,
                    hookContext,
                    null
                );
                throw new Error(errorMessage);
            }

            logger.info(
                `[Before Hook] Successfully processed`,
                hookContext.flagKey,
                hashedEmail
            );
        }).catch(err => {
            logger.error(
                `[Before Hook] Logging System error: ` + err.message,
                hookContext.flagKey,
                null,
                hookContext,
                null
            );
        });
    }

    after(hookContext: HookContext, evaluationDetails: EvaluationDetails<FlagValue>) {

        const emailHashPromise = EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string);

        emailHashPromise.then(hashedEmail => {
            logger.debug(
                `[After Hook] Snapshot before processing.`,
                hookContext.flagKey,
                hashedEmail,
                hookContext,
                evaluationDetails
            );

            if (hookContext.context['shouldAfterHookFailed'] as boolean) {
                // Evaluation 已成功，但是 After Hook 的自定義後處理(檢查Hash值是否合法)失敗。
                const errorMessage = `[After Hook] Hash Check Failed for flag ${hookContext.flagKey}`;
                logger.error(
                    errorMessage,
                    hookContext.flagKey,
                    hashedEmail,
                    hookContext,
                    evaluationDetails
                );
                throw new Error(errorMessage);
            }

            logger.info(
                `[After Hook] Validated successfully.`,
                hookContext.flagKey,
                hashedEmail
            );
        }).catch(err => {
            logger.error(
                `[After Hook] Logging System error: ` + err.message,
                hookContext.flagKey,
                null,
                hookContext,
                evaluationDetails
            );
        });
    }

    error(hookContext: HookContext, err: Error) {
        const emailHashPromise = EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string);

        emailHashPromise.then(hashedEmail => {
            logger.error(
                `[Error Hook] ` + err.message,
                hookContext.flagKey,
                hashedEmail,
                hookContext,
                null
            );
        }).catch(hashError => {
            logger.error(
                `[Error Hook] Logging System error: ` + hashError.message,
                hookContext.flagKey,
                null,
                hookContext,
                null
            );
        });
    }

    finally(hookContext: HookContext) {
        logger.flushLog().then(async () => {
            logger.info(
                `[Finally Hook] Log flushed for flag ${hookContext.flagKey}`,
                hookContext.flagKey,
                await EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string)
            );
        }).catch(async err => {
            logger.error(
                `[Finally Hook] Error flushing log: ` + err.message,
                hookContext.flagKey,
                await EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string),
                hookContext,
                null
            );
        });
    }
}