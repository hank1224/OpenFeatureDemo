// EncryptEmailHook.ts
import type { Hook, HookContext, EvaluationDetails, FlagValue } from "@openfeature/web-sdk";
import EmailHasher from "./EmailHasher";

export class EncryptEmailHook implements Hook {
    before(hookContext: HookContext) {
        const userEmailInput = hookContext.context['userEmailInput'] as string
        const shouldBeforeHookFailed = hookContext.context['shouldBeforeHookFailed'] as boolean

        if (shouldBeforeHookFailed) {
            // 模擬 Before Hook 的自定義前處理(Hash Email)失敗。
            throw new Error('[Before Hook] Hasher Failed, shouldBeforeHookFailed is true')
        }

        // 由於這裡的 context 是 readonly 的，無法做替換 userEmailInput 的動作。
        // 僅在log中顯示 Hashed Email。

        if (shouldBeforeHookFailed) {
            // 模擬 Before Hook 的自定義前處理(Hash Email)失敗。
            throw new Error('[Before Hook] Hasher Failed, shouldBeforeHookFailed is true')
        }

    }

    after(hookContext: HookContext, evaluationDetails: EvaluationDetails<FlagValue>) {
        const shouldAfterHookFailed = hookContext.context['shouldAfterHookFailed'] as boolean

        if (shouldAfterHookFailed) {
            // Evaluation 已成功，但是 After Hook 的自定義後處理(檢查Hash值是否合法)失敗。
            throw new Error('[After Hook] Hash Check Failed, shouldAfterHookFailed is true')
        }
    }

    error(hookContext: HookContext, err: Error) {
        console.log('[Error Hook] Error:', err ,'userEmailInputHashed:', EmailHasher.hashEmail(hookContext.context['userEmailInput'] as string))
    }

    finally(hookContext: HookContext) {
        // console.log('[Finally Hook] Logged by RedisLogger:', RedisLogger)
    }
}