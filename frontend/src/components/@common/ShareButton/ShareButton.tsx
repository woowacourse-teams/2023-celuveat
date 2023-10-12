import { styled } from 'styled-components';
import { LinkProps } from '~/@types/meta.types';
import { shareKakaoLink } from '~/utils/share';

interface ShareButtonProps {
  type: 'kakao';
  children: React.ReactNode;
  meta: LinkProps;
}

function ShareButton({ type, children, meta }: ShareButtonProps) {
  const onClickShareButton = (metaTag: LinkProps) => {
    console.log(metaTag);
    switch (type) {
      case 'kakao':
        shareKakaoLink(metaTag);
        break;
      default:
        throw new Error('공유 버튼이 없습니다.');
    }
  };

  return (
    <StyledShareButton type="button" onClick={() => onClickShareButton(meta)}>
      {children}
    </StyledShareButton>
  );
}

export default ShareButton;

const StyledShareButton = styled.button`
  border: none;
  background: none;
  outline: none;
`;
