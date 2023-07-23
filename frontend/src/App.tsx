import { Wrapper } from '@googlemaps/react-wrapper';
import MainPage from './pages/MainPage';

function App() {
  return (
    <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY}>
      <MainPage />
    </Wrapper>
  );
}

export default App;
