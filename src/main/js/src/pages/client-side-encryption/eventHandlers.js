import { evaluateFeature } from '../openfeature-client-side/featureEvaluator.js';

export function setupEventHandlers() {
    document.addEventListener('DOMContentLoaded', function() {
        const submitButton = document.getElementById('submitBtn');
        if (submitButton) {
            submitButton.addEventListener('click', evaluateFeature);
        }
    });
}