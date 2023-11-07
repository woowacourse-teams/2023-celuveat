/* eslint-disable no-useless-return */
import styled from 'styled-components';
import { useQuery } from '@tanstack/react-query';
import { useLocation } from 'react-router-dom';
import View from '~/assets/icons/view.svg';
import Love from '~/assets/icons/black-love.svg';
import { FONT_SIZE } from '~/styles/common';
import ShareIcon from '~/assets/icons/share.svg';
import ShareButton from '~/components/@common/ShareButton';
import { LinkProps } from '~/@types/meta.types';
import { RestaurantData } from '~/@types/api.types';
import { getRestaurantDetail } from '~/api/restaurant';
import { getImgUrl } from '~/utils/image';
import { getQueryString } from '~/utils/getQueryString';

interface HeaderProps {
  restaurantId: string;
  celebId: string;
}

function Header({ restaurantId, celebId }: HeaderProps) {
  const { pathname } = useLocation();

  const {
    data: { celebs, name, viewCount, likeCount, images },
  } = useQuery<RestaurantData>({
    queryKey: ['restaurantDetail', restaurantId, celebId],
    queryFn: async () => getRestaurantDetail(restaurantId, celebId),
    suspense: true,
  });
  const detailPageUrl = `${pathname}${getQueryString(window.location.search)}`;

  const meta: LinkProps = {
    title: name,
    description: `${celebs[0].name}이(가) 추천한 맛집, ${name}의 정보를 확인해보세요.`,
    imageUrl: getImgUrl(images[0].name, 'webp'),
    link: `${process.env.PUBLIC_URL}${detailPageUrl}`,
  };

  const share = async () => {
    if (typeof navigator.share === 'undefined') {
      await copyClipBoard(`https://celuveat.vercel.app/restaurant/${restaurantId}/${celebId}`);
      return;
    }
    try {
      await navigator.share({ url: `https://celuveat.vercel.app/restaurant/${restaurantId}/${celebId}` });
    } catch (e) {
      if (e.toString().includes('AbortError')) return;
    }
  };

  const copyClipBoard = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      alert('링크가 복사되었어요.');
    } catch (err) {
      alert('클립보드에 저장하는데 문제가 생겼어요.');
    }
  };

  return (
    <StyledDetailHeader tabIndex={0}>
      <StyledTitleSection>
        <h3>{name}</h3>
        <div>
          <StyledShareButton type="button" onClick={share}>
            <ShareIcon />
          </StyledShareButton>
          <ShareButton type="kakao" meta={meta}>
            <img
              width={28}
              height={28}
              src="https://developers.kakao.com/assets/img/about/logos/kakaotalksharing/kakaotalk_sharing_btn_medium.png"
              alt="카카오톡 공유 보내기 버튼"
            />
          </ShareButton>
        </div>
      </StyledTitleSection>
      <div role="group">
        <div aria-label={`조회수 ${viewCount}`}>
          <View width={18} aria-hidden /> <span aria-hidden>{viewCount}</span>
        </div>
        <div aria-label={`좋아요수 ${viewCount}`}>
          <Love width={18} aria-hidden /> <span aria-hidden>{likeCount}</span>
        </div>
      </div>
    </StyledDetailHeader>
  );
}

export default Header;

const StyledDetailHeader = styled.section`
  display: flex;
  flex-direction: column;
  gap: 0.8rem 0;

  padding: 1.2rem 0 2.4rem;

  & > div {
    display: flex;
    align-items: center;
    gap: 0 0.8rem;

    font-size: ${FONT_SIZE.md};

    & > div {
      display: flex;
      align-items: center;
      gap: 0 0.8rem;
    }
  }
`;

const StyledShareButton = styled.button`
  border: none;
  background-color: transparent;
`;

const StyledTitleSection = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;
`;
