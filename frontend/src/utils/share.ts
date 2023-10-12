import { LinkProps } from '~/@types/meta.types';

const registerKakaoKey = () => {
  if (window.Kakao) {
    const kakao = window.Kakao;

    if (!kakao.isInitialized()) {
      kakao.init(process.env.SHARE_KAKAO_LINK_KEY); // 카카오에서 제공받은 javascript key를 넣어줌 -> .env파일에서 호출시킴
    }

    return kakao;
  }

  return null;
};

export const shareKakaoLink = ({ title, description, imageUrl, link }: LinkProps) => {
  const kakao = registerKakaoKey();
  if (!kakao) throw new Error('KaKao 관련 서비스를 제공할 수 없습니다.');

  kakao.Share.sendDefault({
    objectType: 'feed',
    content: {
      title,
      description,
      imageUrl,
      link: {
        mobileWebUrl: link,
        webUrl: link,
      },
    },
  });
};
