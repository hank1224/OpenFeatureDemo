import { provider, isProviderReady } from '../../services/FbProvider.js';
import {setupEventHandlers} from "./eventHandlers";

document.addEventListener('DOMContentLoaded', function() {
    isProviderReady.then(() => {
        console.log('Feature Provider is ready.');
        setupEventHandlers()
    }).catch(() => {
        console.error('Feature Provider is not ready at the time of page load.');
    });
});
