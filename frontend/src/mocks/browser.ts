import { setupWorker } from 'msw';
import { errorHandlers, handlers } from './handlers';

export const worker = setupWorker(...handlers);
