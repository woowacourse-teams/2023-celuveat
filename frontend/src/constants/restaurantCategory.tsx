import All from '~/assets/icons/category/all.svg';
import Asian from '~/assets/icons/category/asian.svg';
import Korean from '~/assets/icons/category/korean.svg';
import Chinese from '~/assets/icons/category/chinese.svg';
import Japanese from '~/assets/icons/category/japanese.svg';
import Beef from '~/assets/icons/category/beef.svg';
import Soup from '~/assets/icons/category/soup.svg';
import Bakery from '~/assets/icons/category/bakery.svg';
import Noodle from '~/assets/icons/category/noodle.svg';
import Pizza from '~/assets/icons/category/pizza.svg';
import Sushi from '~/assets/icons/category/sushi.svg';
import Spaghetti from '~/assets/icons/category/spaghetti.svg';
import Snackbar from '~/assets/icons/category/snackbar.svg';
import Wine from '~/assets/icons/category/wine.svg';
import World from '~/assets/icons/category/world.svg';
import type { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

const RESTAURANT_CATEGORY: Record<string, Category> = {
  all: { label: '전체', icon: <All /> },
  korean: { label: '한식', icon: <Korean /> },
  soup: { label: '탕,찌개', icon: <Soup /> },
  chinese: { label: '중식', icon: <Chinese /> },
  japanese: { label: '일식', icon: <Japanese /> },
  western: { label: '양식', icon: <Spaghetti /> },
  seafood: { label: '회,수산물', icon: <Sushi /> },
  beef: { label: '고기', icon: <Beef /> },
  asian: { label: '아시안', icon: <Asian /> },
  noodle: { label: '면류', icon: <Noodle /> },
  snackbar: { label: '분식', icon: <Snackbar /> },
  fastfood: { label: '패스트푸드', icon: <Pizza /> },
  desert: { label: '커피,디저트', icon: <Bakery /> },
  world: { label: '세계요리', icon: <World /> },
  bar: { label: '주점', icon: <Wine /> },
};
export default RESTAURANT_CATEGORY;
