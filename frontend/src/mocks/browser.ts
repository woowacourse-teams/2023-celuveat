import { setupWorker } from 'msw';
import {
  errorPostLike400handlers,
  errorPostLike403Handlers,
  errorPostLike404Handlers,
  errorPostLike500Handlers,
  error401handlers,
  handlers,
} from './handlers';

// 좋아요 성공 시
export const worker = setupWorker(...handlers);

// 400 에러
// export const worker = setupWorker(...errorPostLike400handlers);

// 403에러
// export const worker = setupWorker(...errorPostLike403Handlers);

// 404에러
// export const worker = setupWorker(...errorPostLike404Handlers);

// 500에러
// export const worker = setupWorker(...errorPostLike500Handlers);
