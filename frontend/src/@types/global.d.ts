declare module '*.png';
declare module '*.woff2';
declare module '*.svg' {
  import { ReactElement, SVGProps } from 'react';

  const content: (props: SVGProps<SVGElement>) => ReactElement;
  export default content;
}

interface Window {
  kakao: any;
}
