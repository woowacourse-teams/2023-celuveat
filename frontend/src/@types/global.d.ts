/* eslint-disable @typescript-eslint/no-explicit-any */

declare module '*.png';
declare module '*.woff2';
declare module '*.svg' {
  import * as React from 'react';

  const ReactComponent: React.FunctionComponent<React.SVGProps<SVGSVGElement>>;

  export default ReactComponent;
}

interface Window {
  Kakao: any;
}
