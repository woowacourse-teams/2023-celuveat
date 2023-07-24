/* eslint-disable @typescript-eslint/no-explicit-any */

declare module '*.png';
declare module '*.woff2';
declare module '*.svg' {
  import * as React from 'react';

  export const ReactComponent: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;

  const src: string;
  export default src;
}

interface Window {
  kakao: any;
}
