import {OpenFeature, ProviderEvents} from '@openfeature/web-sdk';

export async function evaluateFeature() {
    try {
        const customerId = document.getElementById('customerId').value;
        const user = {
            name: 'Case4',
            keyId: 'OpenFeatureDemoClient',
            customizedProperties: [],
        };

        document.getElementById('result').innerText = '';
        document.getElementById('errorLog').innerText = '';

        OpenFeature.addHandler(ProviderEvents.ConfigurationChanged, (eventDetails) => {
            const client = OpenFeature.getClient();
            const context = { customerId: customerId };
            const flagValue = client.getBooleanValue('featbit-button', false);
            console.log({...eventDetails, value});
        });

        const client = OpenFeature.getClient();
        const flagValue = client.getBooleanValue('featbit-button', false);
        document.getElementById('result').innerText = `Feature flag result: ${flagValue}`;

    } catch (error) {
        console.error(error);
        document.getElementById('errorLog').innerText = `Error: ${error.message}`;
    }
}