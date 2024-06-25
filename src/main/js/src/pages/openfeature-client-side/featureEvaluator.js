// featureEvaluator.js
import {OpenFeature, ProviderEvents} from '@openfeature/web-sdk';
import {evalContent } from "./getEvalContent";

export async function evaluateFeature() {
    try {
        document.getElementById('result').innerText = '';
        document.getElementById('errorLog').innerText = '';

        // handler 處理 Flag 變更事件，收到 Flag 變更事件後由這裡接手。
        OpenFeature.addHandler(ProviderEvents.ConfigurationChanged, (eventDetails) => {
            const client = OpenFeature.getClient();
            try {
                // client在前端取得時並不需要 await, 因為在設置Provider時已經載入了整個快取，僅有Provider的初始化需要 await
                // 見：https://openfeature.dev/blog/catering-to-the-client-side/#client-side-evaluation-tomorrow
                const flagValue = client.getStringValue('openfeature-client-side', 'Failed, showing defaultValue.');
                console.log({...eventDetails, flagValue});
                document.getElementById('result').innerText = `flagValue(ConfigurationChanged): ${flagValue}`;
            } catch (error) {
                console.error(error);
                document.getElementById('errorLog').innerText = `Error in event handler: ${error.message}`;
            }
        });

        // 網頁第一次載入直接跟 server 取值寫入 Cache
        const client = OpenFeature.getClient();
        const flagValue = client.getStringValue('openfeature-client-side', 'Failed, showing defaultValue.');
        console.log({flagValue});
        document.getElementById('result').innerText = `flagValue: ${flagValue}`;

    } catch (error) {
        console.error(error);
        document.getElementById('errorLog').innerText = `Error: ${error.message}`;
    }
}