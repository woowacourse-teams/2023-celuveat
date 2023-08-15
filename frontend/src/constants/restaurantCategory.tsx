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
import type { RestaurantCategory } from '~/@types/restaurant.types';

interface Category {
  label: RestaurantCategory;
  icon: React.ReactNode;
}

const RESTAURANT_CATEGORY: Category[] = [
  {
    label: '일식당',
    icon: <Japanese width={24} />,
  },
  {
    label: '한식',
    icon: <Korean width={24} />,
  },
  {
    label: '초밥,롤',
    icon: <Sushi width={24} />,
  },
  {
    label: '생선회',
    icon: <Sashimi width={24} />,
  },
  {
    label: '양식',
    icon: <Pasta width={24} />,
  },
  {
    label: '와인',
    icon: <Wine width={24} />,
  },
  {
    label: '육류,고기요리',
    icon: <Meat width={24} />,
  },
  {
    label: '이자카야',
    icon: <Ijakaya width={24} />,
  },
  {
    label: '돼지고기구이',
    icon: <Pig width={24} />,
  },
  {
    label: '요리주점',
    icon: <Pub width={24} />,
  },
];

export default RESTAURANT_CATEGORY;
