import {OpenFeature, ProviderEvents} from '@openfeature/web-sdk';

export async function evaluateFeature() {
    try {
        const clientLevelValue = document.getElementById('clientLevelValue').value;

        document.getElementById('result').innerText = '';
        document.getElementById('errorLog').innerText = '';

        OpenFeature.addHandler(ProviderEvents.ConfigurationChanged, (eventDetails) => {
            const client = OpenFeature.getClient();
            const context = { clientLevelValue: clientLevelValue };
            const flagValue = client.getStringValue('client-level-value', '1', context);
            console.log({...eventDetails, value});
        });

        const client = OpenFeature.getClient();
        const options = {
            clientLevelValue: clientLevelValue
        };
        const flagValue = client.getStringValue('client-level-value', '1', options);
        document.getElementById('result').innerText = `Feature flag result: ${flagValue}`;

    } catch (error) {
        console.error(error);
        document.getElementById('errorLog').innerText = `Error: ${error.message}`;
    }
}