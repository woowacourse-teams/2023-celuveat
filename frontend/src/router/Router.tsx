import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { lazy } from 'react';

const Root = lazy(() => import('./Root'));
const EventPageDetail = lazy(() => import('~/pages/EventPageDetail'));
const NotFoundPage = lazy(() => import('~/pages/NotFoundPage'));
const MapPage = lazy(() => import('~/pages/MapPage'));
const MainPage = lazy(() => import('~/pages/MainPage'));
const RestaurantDetailPage = lazy(() => import('~/pages/RestaurantDetailPage/index'));
const SignUpPage = lazy(() => import('~/pages/SignUpPage'));
const UserPage = lazy(() => import('~/pages/UserPage'));
const UpdatedRestaurantPage = lazy(() => import('~/pages/UpdatedRestaurantPage'));
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
      errorElement: <NotFoundPage />,
      children: [
        { index: true, element: <MainPage /> },
        { path: '/category/:category', element: <CategoryResultPage /> },
        { path: '/celeb/:celebId', element: <CelebResultPage /> },
        { path: '/event', element: <EventPageDetail /> },
        { path: '/map', element: <MapPage /> },
        { path: '/policy', element: <PrivacyPolicy /> },
        { path: '/region/:region', element: <RegionResultPage /> },
        { path: '/restaurants/:id', element: <RestaurantDetailPage /> },
        { path: '/restaurants/like', element: <WishListPage /> },
        { path: '/signUp', element: <SignUpPage /> },
        { path: '/user', element: <UserPage /> },
        { path: '/updated-recent', element: <UpdatedRestaurantPage /> },
        { path: '/withdrawal', element: <WithdrawalPage /> },
        { path: '/oauth/redirect/kakao', element: <OauthRedirectPage type="kakao" /> },
        { path: '/oauth/redirect/google', element: <OauthRedirectPage type="google" /> },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default Router;
