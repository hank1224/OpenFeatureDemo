// staticFbProvider.ts
import {FbProvider} from '@featbit/openfeature-provider-js-client';
import {OpenFeature} from '@openfeature/web-sdk';
import {clientKey, featbitEventUrl, featbitStreamingUrl} from "../../services/FbClientConfig";
import {evalContent} from "./getEvalContent";

const user = {
    name: 'OpenFeatureDemoClient',
    keyId: 'Case4:' + Math.random().toString(8).substring(7),
    customizedProperties: [],
};

const providerOption = {
    sdkKey: clientKey,
    streamingUri: featbitStreamingUrl,
    eventsUri: featbitEventUrl,
    user: user // FbProvider 必須要有 user 來進行評估，是該供應商的使用規則。
};

console.log('FbProvider initialization options:', providerOption);
export async function staticFbProvider() {
    await OpenFeature.setContext(evalContent);
    // Client-side 的 Evaluation Context 在 Provider 初始化時被設置，後續的 Client instance 會使用這個，也可再覆蓋。
    // Server-side 的 Evaluation Context 在 建立 Client instance 時被設置。
    await OpenFeature.setProvider(new FbProvider(providerOption)); // using evalContent
}