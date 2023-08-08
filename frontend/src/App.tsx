import { BrowserRouter, Routes, Route } from 'react-router-dom';
import OauthRedirectPage from '~/pages/OauthRedirectPage';
import MainPage from '~/pages/MainPage';
import RestaurantDetail from './pages/RestaurantDetail';

export const { BASE_URL } = process.env;

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/restaurants/:id" element={<RestaurantDetail />} />
        <Route path="/oauth/redirect/kakao" element={<OauthRedirectPage type="kakao" />} />
        <Route path="/oauth/redirect/naver" element={<OauthRedirectPage type="naver" />} />
        <Route path="/oauth/redirect/google" element={<OauthRedirectPage type="google" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
