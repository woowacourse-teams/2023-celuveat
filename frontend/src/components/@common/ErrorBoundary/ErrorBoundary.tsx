// import { Component, ReactElement, ReactNode } from 'react';

// interface FallbackRenderProps {
//   resetErrorBoundary: () => void;
// }
// interface Props {
//   children: ReactNode;
//   fallbackRender: ({ resetErrorBoundary }: FallbackRenderProps) => ReactElement;
//   reset: () => void;
// }

// interface State {
//   hasError: boolean;
// }

// class ErrorBoundary extends Component<Props, State> {
//   constructor(props: Props) {
//     super(props);
//     this.state = { hasError: false };
//   }

//   static getDerivedStateFromError(): State {
//     return {
//       hasError: true,
//     };
//   }

//   resetErrorBoundary() {
//     const { reset } = this.props;
//     reset();
//     this.setState({ hasError: false });
//   }

//   render() {
//     const { hasError } = this.state;
//     const { children, fallbackRender } = this.props;

//     if (hasError) {
//       return fallbackRender({ resetErrorBoundary: () => this.resetErrorBoundary() });
//     }

//     return children;
//   }
// }

// export default ErrorBoundary;
