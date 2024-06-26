import {EvaluationDetails, FlagValue, Hook, HookContext} from "@openfeature/web-sdk";

export class EncryptEmailHook implements Hook {
    before(hookContext: HookContext) {
        // code to run before flag evaluation
    }

    after(hookContext: HookContext, evaluationDetails: EvaluationDetails<FlagValue>) {
        // code to run after successful flag evaluation
    }

    error(hookContext: HookContext, err: Error) {
        // code to run if there's an error during before hooks or during flag evaluation
    }

    finally(hookContext: HookContext) {
        // code to run after all other stages, regardless of success/failure
    }
}