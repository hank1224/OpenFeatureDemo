// EncryptEmailHook.ts
import type { Hook, HookContext, EvaluationDetails, FlagValue } from "@openfeature/web-sdk";
import { CachedLogger } from "./CachedLogger";
import EmailHasher from "./EmailHasher";

/* ！！！注意！！！
    此程式的 Error Hook 在 async 下無法被觸發，
    暫時Hardcode: 每個Hook最下方設置同步的 Fail 方法
    但這會導致整個程式碼超沒有邏輯，
    目前沒有解法。
*/

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
            console.log('BeforeHook Promise Error: ' + err.message);
        });

        /* Promise 內的 Error 無法向上傳遞，直接 Hard Fail。*/
        if (hookContext.context['shouldBeforeHookFailed'] as boolean){
            // 模擬 Before Hook 的自定義前處理(Hash Email)失敗。
            throw new Error('[Before Hook] (Hardcode) Hasher Failed.');
        }
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
                const errorMessage = `[After Hook] Hash Valid Check Failed.`;
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
            console.log('AfterHook Promise Error: ' + err.message);
        });

        /* Promise 內的 Error 無法向上傳遞，直接 Hard Fail。*/
        if (hookContext.context['shouldAfterHookFailed'] as boolean){
            // Evaluation 已成功，但是 After Hook 的自定義後處理(檢查Hash值是否合法)失敗。
            throw new Error('[After Hook] (Hardcode) Hash Valid Check Failed.');
        }
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
            console.log('ErrorHook Promise Error: ' + hashError.message);
        });
    }

    async finally(hookContext: HookContext) {
        const userEmailHashed = await EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string);

        logger.info(
            `[Finally Hook] Preparing to flush log.`,
            hookContext.flagKey,
            userEmailHashed
        );

        await logger.flushLog().catch(err => {
            console.log('FinallyHook flushLog Error: ' + err.message);
        });
    }
}