// FbProvider.js
import { FbProvider } from '@featbit/openfeature-provider-js-client';
import { OpenFeature } from '@openfeature/web-sdk';
import { clientKey, featbitStreamingUrl, featbitEventUrl } from './FbClientConfig';

// 添加用户信息
const user = {
    name: 'Case4',
    keyId: 'OpenFeatureDemoClient',
    customizedProperties: [],
};

const option = {
    sdkKey: clientKey,
    streamingUri: featbitStreamingUrl,
    eventsUri: featbitEventUrl,
    user: user,
};

const provider = new FbProvider(option);

let isProviderReady = new Promise((resolve) => {
    OpenFeature.setProviderAndWait(provider).then(() => {
        console.log('FbProvider is ready');
        resolve();
    });
});

export { provider, isProviderReady };