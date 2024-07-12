import EmailHasher from "./EmailHasher";
import {OpenFeature} from "@openfeature/web-sdk";

export function setupEventHandlers() {
    const userEmailInput = document.getElementById('userEmailInput') as HTMLInputElement;
    const userEmailHashPreview = document.getElementById('userEmailHashPreview') as HTMLInputElement;
    // 即時更新 Hash Preview：監聽 userEmailInput 的輸入事件
    userEmailInput.addEventListener('input', async function() {
        const email = userEmailInput.value.trim();
        if (email) {
            userEmailHashPreview.value = await EmailHasher.hashEmail(email);
        } else {
            userEmailHashPreview.value = '';
        }
    });

    // 是否前往第二步：監聽 userEmailInputBtn 的點擊事件
    document.getElementById('userEmailInputBtn').addEventListener('click', async function () {
        const userEmailInputValue = userEmailInput.value;
        await OpenFeature.setContext({userEmailInput: userEmailInputValue});

        const evalOption = document.getElementById('evalOption') as HTMLElement;
        const evalResult = document.getElementById('evalResult') as HTMLElement;
        if (userEmailInputValue.trim() !== '') {
            evalOption.style.display = 'block';
            evalResult.style.display = 'none';
        } else {
            console.log('userEmailInputBtn Listener: userEmailInput is empty.');
            evalOption.style.display = 'none';
            evalResult.style.display = 'none';
        }
    });

    // 重設頁面：監聽 evalOptionSubmitBtn 的點擊事件
    document.getElementById('evalOptionResetBtn').addEventListener('click', function() {
        const evalOption = document.getElementById('evalOption') as HTMLElement;
        const evalResult = document.getElementById('evalResult') as HTMLElement;
        evalOption.style.display = 'none';
        evalResult.style.display = 'none';
        (document.getElementById('userEmailInput') as HTMLInputElement).value = 'someone@example.com';
    });

    // 送往Server-side進行評估：監聽 serverSideEvalBtn 的點擊事件
    document.getElementById('evalOptionForm').addEventListener('submit', function() {
        const userEmailInput = document.getElementById('userEmailInput') as HTMLInputElement;
        const userEmailSubmit = document.getElementById('userEmailSubmit') as HTMLInputElement;

        if (userEmailInput && userEmailSubmit) {
            userEmailSubmit.value = userEmailInput.value;
        }
    });


    //本地Client-side評估：監聽 clientSideEvalBtn 的點擊事件
    document.getElementById('clientSideEvalBtn').addEventListener('click', async function () {
        // Reset evalResult text
        const updateElementText = (elementId: string, text: string) => {
            const element = document.getElementById(elementId);
            if (element) {
                element.innerText = text;
            }
        };
        updateElementText('flagKey', '');
        updateElementText('value', '');
        updateElementText('reason', '');
        updateElementText('variant', '');
        updateElementText('errorCode', '');
        updateElementText('errorMessage', '');


        // Get EvalContext
        const userEmailInput = document.getElementById('userEmailInput') as HTMLInputElement;
        const userEmailInputValue = userEmailInput.value;

        const shouldFlagEvalFailed = (document.querySelector('input[name="shouldFlagEvalFailed"]:checked') as HTMLInputElement).value === 'true';
        const shouldBeforeHookFailed = (document.querySelector('input[name="shouldBeforeHookFailed"]:checked') as HTMLInputElement).value === 'true';
        const shouldAfterHookFailed = (document.querySelector('input[name="shouldAfterHookFailed"]:checked') as HTMLInputElement).value === 'true';

        await OpenFeature.setContext({
            userEmailInput: userEmailInputValue,
            shouldBeforeHookFailed: shouldBeforeHookFailed,
            shouldAfterHookFailed: shouldAfterHookFailed
        });


        // Do Client-side Eval
        const client = OpenFeature.getClient("FbProvider") // Hook 已在 Provider 設定全域套用

        // 是否觸發 FlagEvalFailed：FlagKey不存在場景。
        const flagKey = shouldFlagEvalFailed ? 'before-hook-email-crypto-not-exist' : 'before-hook-email-crypto';
        const boolDetails = await client.getBooleanDetails(flagKey, false);

        // Page 顯示 ＆ null check
        const evalOption = document.getElementById('evalOption');
        const evalResult = document.getElementById('evalResult');
        if (userEmailInputValue.trim() !== '') {
            evalResult.style.display = 'block';
        } else {
            console.log('clientSideEvalBtn Listener: userEmailInput is empty, setting stage back to Input Stage.');
            evalOption.style.display = 'none';
            evalResult.style.display = 'none';
        }

        // show Eval Result
        updateElementText('flagKey', `Flag Key: ${boolDetails.flagKey}`);
        updateElementText('value', `Value: ${boolDetails.value}`);
        updateElementText('reason', `Reason: ${boolDetails.reason || 'N/A'}`);
        updateElementText('variant', `Variant: ${boolDetails.variant || 'N/A'}`);
        updateElementText('errorCode', `Error Code: ${boolDetails.errorCode || 'N/A'}`);
        updateElementText('errorMessage', `Error Message: ${boolDetails.errorMessage || 'N/A'}`);
    });
}