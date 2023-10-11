import { useLocation, useNavigate } from 'react-router-dom';
import { getQueryString } from '../utils/getQueryString';

const useNavigateSignUp = () => {
  const navigator = useNavigate();
  const { pathname } = useLocation();

  const goSignUp = () => {
    navigator('/signUp', { state: { from: `${pathname}${getQueryString(window.location.search)}` } });
  };

  return {
    goSignUp,
  };
};

export default useNavigateSignUp;
