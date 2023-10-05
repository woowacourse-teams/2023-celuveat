import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { lazy } from 'react';

const Root = lazy(() => import('./Root'));
const MapPage = lazy(() => import('~/pages/MapPage'));
const MainPage = lazy(() => import('~/pages/MainPage'));
const RestaurantDetailPage = lazy(() => import('~/pages/RestaurantDetailPage'));
const SignUpPage = lazy(() => import('~/pages/SignUpPage'));
const WishListPage = lazy(() => import('~/pages/WishListPage'));
const WithdrawalPage = lazy(() => import('~/pages/WithdrawalPage'));
const PrivacyPolicy = lazy(() => import('~/pages/PrivacyPolicyPage'));
const OauthRedirectPage = lazy(() => import('~/pages/OauthRedirectPage'));
const RegionResultPage = lazy(() => import('~/pages/RegionResultPage'));
const CelebResultPage = lazy(() => import('~/pages/CelebResultPage'));
const CategoryResultPage = lazy(() => import('~/pages/CategoryResultPage'));

function Router() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <Root />,
      children: [
        { index: true, element: <MainPage /> },
        { path: '/map', element: <MapPage /> },
        { path: '/region/:region', element: <RegionResultPage /> },
        { path: '/celeb/:celebId', element: <CelebResultPage /> },
        { path: '/category/:category', element: <CategoryResultPage /> },
        { path: '/policy', element: <PrivacyPolicy /> },
        { path: '/restaurants/:id', element: <RestaurantDetailPage /> },
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
