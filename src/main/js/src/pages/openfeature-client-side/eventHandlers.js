// eventHandlers.ts
import { evaluateFeature } from './featureEvaluator';

export function setupEventHandlers() {
    const submitButton = document.getElementById('submitBtn');
    if (submitButton) {
        submitButton.addEventListener('click', evaluateFeature);
    }
}