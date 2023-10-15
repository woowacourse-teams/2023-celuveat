import Next from '~/assets/icons/arrow/next.svg';
import Prev from '~/assets/icons/arrow/prev.svg';

export const CelebCarouselSettings = {
  dots: true,
  arrows: true,
  infinite: true,
  speed: 500,
  slidesToShow: 11,
  slidesToScroll: 11,
  nextArrow: <Next />,
  prevArrow: <Prev />,
  responsive: [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 9,
        slidesToScroll: 9,
      },
    },
    {
      breakpoint: 912, // surface Pro7
      settings: {
        slidesToShow: 7,
        slidesToScroll: 7,
      },
    },
    {
      breakpoint: 820, // ipad air
      settings: {
        slidesToShow: 6,
        slidesToScroll: 6,
      },
    },
  ],
};

export const VideoCarouselSettings = {
  arrows: true,
  dots: true,
  infinite: true,
  speed: 1000,
  slidesToShow: 3,
  slidesToScroll: 3,
  nextArrow: <Next />,
  prevArrow: <Prev />,
  responsive: [
    {
      breakpoint: 1180,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 2,
      },
    },
  ],
};

export const RestaurantCardCarouselSettings = {
  arrows: true,
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 5,
  slidesToScroll: 5,
  nextArrow: <Next />,
  prevArrow: <Prev />,
  responsive: [
    {
      breakpoint: 1180,
      settings: {
        slidesToShow: 4,
        slidesToScroll: 4,
      },
    },
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 3,
      },
    },
    {
      breakpoint: 780, // ipad air
      settings: {
        slidesToShow: 2,
        slidesToScroll: 2,
      },
    },
  ],
};

export const BannerCarouselSettings = {
  arrows: true,
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  autoplay: true,
  nextArrow: <Next />,
  prevArrow: <Prev />,
};

export const RegionCarouselSettings = {
  dots: true,
  arrows: true,
  infinite: true,
  speed: 500,
  slidesToShow: 10,
  slidesToScroll: 10,
  nextArrow: <Next />,
  prevArrow: <Prev />,
  responsive: [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 9,
        slidesToScroll: 9,
      },
    },
    {
      breakpoint: 912, // surface Pro7
      settings: {
        slidesToShow: 7,
        slidesToScroll: 7,
      },
    },
    {
      breakpoint: 820, // ipad air
      settings: {
        slidesToShow: 6,
        slidesToScroll: 6,
      },
    },
  ],
};
