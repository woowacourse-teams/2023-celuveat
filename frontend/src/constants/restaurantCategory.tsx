import All from '~/assets/icons/category/all.svg';
import Asian from '~/assets/icons/category/asian.svg';
import Korean from '~/assets/icons/category/korean.svg';
import Chinese from '~/assets/icons/category/chinese.svg';
import Japanese from '~/assets/icons/category/japanese.svg';
import Beef from '~/assets/icons/category/beef.svg';
import Soup from '~/assets/icons/category/soup.svg';
import Noodle from '~/assets/icons/category/noodle.svg';
import Pizza from '~/assets/icons/category/pizza.svg';
import Sushi from '~/assets/icons/category/sushi.svg';
import Spaghetti from '~/assets/icons/category/spaghetti.svg';
import Snackbar from '~/assets/icons/category/snackbar.svg';
import Wine from '~/assets/icons/category/wine.svg';
import World from '~/assets/icons/category/world.svg';

interface Category {
  label: string;
  icon: React.ReactNode;
}

const RESTAURANT_CATEGORY: Category[] = [
  { label: '전체', icon: <All /> },
  { label: '한식', icon: <Korean /> },
  { label: '탕, 찌개', icon: <Soup /> },
  { label: '중식', icon: <Chinese /> },
  { label: '일식', icon: <Japanese /> },
  { label: '양식', icon: <Spaghetti /> },
  { label: '회, 수산물', icon: <Sushi /> },
  { label: '고기', icon: <Beef /> },
  { label: '아시안', icon: <Asian /> },
  { label: '면류', icon: <Noodle /> },
  { label: '분식', icon: <Snackbar /> },
  { label: '치킨, 피자, 햄버거', icon: <Pizza /> },
  // { label: '커피, 디저트, 빵', icon: <Bakery /> },
  { label: '세계요리', icon: <World /> },
  { label: '주점', icon: <Wine /> },
];

export default RESTAURANT_CATEGORY;
