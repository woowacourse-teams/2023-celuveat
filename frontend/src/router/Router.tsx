import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { lazy } from 'react';

const Root = lazy(() => import('./Root'));
const MainPage = lazy(() => import('~/pages/MainPage'));
const RestaurantDetail = lazy(() => import('~/pages/RestaurantDetail'));
const SignUpPage = lazy(() => import('~/pages/SignUpPage'));
const WishListPage = lazy(() => import('~/pages/WishListPage'));
const WithdrawalPage = lazy(() => import('~/pages/WithdrawalPage'));
const PrivacyPolicy = lazy(() => import('~/pages/PrivacyPolicyPage'));
const OauthRedirectPage = lazy(() => import('~/pages/OauthRedirectPage'));

function Router() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <Root />,
      // errorElement: <NotFound />,
      children: [
        { index: true, element: <MainPage /> },
        { path: '/policy', element: <PrivacyPolicy /> },
        { path: '/restaurants/:id', element: <RestaurantDetail /> },
        { path: '/signUp', element: <SignUpPage /> },
        { path: '/restaurants/like', element: <WishListPage /> },
        { path: '/withdrawal', element: <WithdrawalPage /> },
        { path: '/oauth/redirect/kakao', element: <OauthRedirectPage type="kakao" /> },
        { path: '/oauth/redirect/google', element: <OauthRedirectPage type="google" /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default Router;
