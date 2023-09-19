import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import MainPage from '~/pages/MainPage';
import RestaurantDetail from '~/pages/RestaurantDetail';
import WishListPage from '~/pages/WishListPage';
import SignUpPage from '~/pages/SignUpPage';
import WithdrawalPage from '~/pages/WithdrawalPage';
import PrivacyPolicy from '~/pages/PrivacyPolicyPage';
import OauthRedirectPage from '~/pages/OauthRedirectPage';
import Root from './Root';

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
