import Ijakaya from '~/assets/icons/restaurantCategory/ijakaya.svg';
import Japanese from '~/assets/icons/restaurantCategory/japanese.svg';
import Korean from '~/assets/icons/restaurantCategory/korean.svg';
import Meat from '~/assets/icons/restaurantCategory/meat.svg';
import Pasta from '~/assets/icons/restaurantCategory/pasta.svg';
import Pig from '~/assets/icons/restaurantCategory/pig.svg';
import Pub from '~/assets/icons/restaurantCategory/pub.svg';
import Sashimi from '~/assets/icons/restaurantCategory/sashimi.svg';
import Sushi from '~/assets/icons/restaurantCategory/sushi.svg';
import Wine from '~/assets/icons/restaurantCategory/wine.svg';
import All from '~/assets/icons/restaurantCategory/all.svg';
import type { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

const RESTAURANT_CATEGORY: Category[] = [
  {
    label: '전체',
    icon: <All />,
  },
  {
    label: '일식당',
    icon: <Japanese />,
  },
  {
    label: '한식',
    icon: <Korean />,
  },
  {
    label: '초밥,롤',
    icon: <Sushi />,
  },
  {
    label: '생선회',
    icon: <Sashimi />,
  },
  {
    label: '양식',
    icon: <Pasta />,
  },
  {
    label: '와인',
    icon: <Wine />,
  },
  {
    label: '육류,고기요리',
    icon: <Meat />,
  },
  {
    label: '이자카야',
    icon: <Ijakaya />,
  },
  {
    label: '돼지고기구이',
    icon: <Pig />,
  },
  {
    label: '요리주점',
    icon: <Pub />,
  },
];

export default RESTAURANT_CATEGORY;
