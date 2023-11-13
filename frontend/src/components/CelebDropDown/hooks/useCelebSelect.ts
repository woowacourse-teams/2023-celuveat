import { useState } from 'react';
import { shallow } from 'zustand/shallow';
import { OPTION_FOR_CELEB_ALL } from '~/constants/options';
import useRestaurantsQueryStringState from '~/hooks/store/useRestaurantsQueryStringState';

import { Celeb } from '~/@types/celeb.types';

export const useCelebSelect = () => {
  const [selectedCeleb, setSelectedCeleb] = useState<Celeb>(OPTION_FOR_CELEB_ALL);

  const [setCelebId, setCurrentPage] = useRestaurantsQueryStringState(
    state => [state.setCelebId, state.setCurrentPage],
    shallow,
  );

  const selectCeleb = (celeb: Celeb) => () => {
    setCelebId(celeb.id);
    setCurrentPage(0);

    setSelectedCeleb(celeb);
  };

  return {
    selectedCeleb,
    selectCeleb,
  };
};
