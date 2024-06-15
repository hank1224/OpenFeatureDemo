import { FbProvider } from '@featbit/openfeature-provider-js-client';
import { OpenFeature, ProviderEvents } from '@openfeature/web-sdk';

const clientKey = document.getElementById('config').dataset.clientKey;
const featbitStreamingUrl = document.getElementById('config').dataset.featbitStreamingUrl;
const featbitEventUrl = document.getElementById('config').dataset.featbitEventUrl;

console.log('clientKey:', clientKey);
console.log('featbitStreamingUri:', featbitStreamingUrl);
console.log('featbitEventUri:', featbitEventUrl);

document.addEventListener('DOMContentLoaded', function() {
    const submitButton = document.getElementById('submitBtn');
    if (submitButton) {
        submitButton.addEventListener('click', evaluateFeature);
    }
});

async function evaluateFeature() {
    try {
        const customerId = document.getElementById('customerId').value;

        const streamingUri = featbitStreamingUrl;
        const eventsUri = featbitEventUrl;
        const sdkKey = clientKey;

        const user = {
            name: 'OpenFeatureDemoClient',
            keyId: 'Case4',
            // customizedProperties: [
            //     { key: 'customerId', value: 'Hank' },
            //     // 範例加密資料，這裡使用base64編碼演示解碼，不具加密作用！
            //     { key: 'encryption-data', value: '6YCZ5piv6IO96J+y77yM6IO96J+y5bGs5pa85piG6J+y57ax6Z6Y57+F55uu77yM6Zuc6aOf6Z2e5bi45Ye25q6Y77yM6auU5YaF57aT5bi45pyJ5b6I5aSa55eF5q+S44CC5Zyo5ZSQ5pyd5bCx5bey57aT5Ye654++77yM6KKr5YqN5a6i5p2O55m95LiA5YqN5q665q2744CC5pyJ5paH54276KiY6LyJ77ya6KaB5piv6IO96J+y5L6G77yM5oiR6KaB6YG45p2O55m944CC'}
            // ],
        };

        const option = {
            sdkKey: sdkKey,
            streamingUri: streamingUri,
            eventsUri: eventsUri,
            user: user,
        };

        // Clear previous results and error messages
        document.getElementById('result').innerText = '';
        document.getElementById('errorLog').innerText = '';


        // Create provider
        const provider = new FbProvider(option);

        // uncomment this line if you want to set a different context
        // await OpenFeature.setContext(user);

        await OpenFeature.setProviderAndWait(provider);

        OpenFeature.addHandler(ProviderEvents.ConfigurationChanged, (eventDetails) => {
            const client = OpenFeature.getClient();
            const value = client.getStringValue('robot', 'ChatGPT');
            console.log({...eventDetails, value});
        });

        // Create client
        const client = OpenFeature.getClient();

        // Set evaluation context
        const context = { customerId: customerId };

        // Evaluate feature flag
        const flagValue = client.getBooleanValue('featbit-button', false, context);

        // Display results
        document.getElementById('result').innerText = `Feature flag result: ${flagValue}`;
    } catch (error) {
        console.error(error);
        // Display error logs on the page
        document.getElementById('errorLog').innerText = `Error: ${error.message}`;
    }
}