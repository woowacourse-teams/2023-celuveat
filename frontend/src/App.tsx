import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Toast from '~/components/@common/Toast';
import MainPage from '~/pages/MainPage';
import RestaurantDetail from './pages/RestaurantDetail';
import WishListPage from '~/pages/WishListPage';
import SignUpPage from '~/pages/SignUpPage';
import WithdrawalPage from '~/pages/WithdrawalPage';
import PrivacyPolicy from './pages/PrivacyPolicyPage';
import OauthRedirectPage from '~/pages/OauthRedirectPage';
import ServerSelectBox from './components/ServerSelectBox';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/policy" element={<PrivacyPolicy />} />
        <Route path="/restaurants/:id" element={<RestaurantDetail />} />
        <Route path="/signUp" element={<SignUpPage />} />
        <Route path="/restaurants/like" element={<WishListPage />} />
        <Route path="/withdrawal" element={<WithdrawalPage />} />
        <Route path="/oauth/redirect/kakao" element={<OauthRedirectPage type="kakao" />} />
        <Route path="/oauth/redirect/google" element={<OauthRedirectPage type="google" />} />
      </Routes>
      <Toast />
      {process.env.NODE_ENV === 'development' && <ServerSelectBox />}
    </BrowserRouter>
  );
}

export default App;
