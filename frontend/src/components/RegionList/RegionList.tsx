import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { Region } from '~/@types/region.types';
import { RECOMMENDED_REGION } from '~/constants/recommendedRegion';
import { SERVER_IMG_URL } from '~/constants/url';
import { FONT_SIZE } from '~/styles/common';

function RegionList() {
  const navigate = useNavigate();
  const REGION_LIST = Object.keys(RECOMMENDED_REGION);
  const clickIcon = (region: Region) => {
    navigate(`/result/${region}`);
  };

  return (
    <>
      {REGION_LIST.map((region: Region) => (
        <StyledRegion
          onClick={() => clickIcon(region)}
          imgUrl={`${SERVER_IMG_URL}regions/${region}.jpeg`}
          aria-label={RECOMMENDED_REGION[region]}
        >
          <StyledRegionName>
            {RECOMMENDED_REGION[region].split(',').map(item => (
              <>
                {item}
                <br />
              </>
            ))}
          </StyledRegionName>
          <StyledMask />
        </StyledRegion>
      ))}
    </>
  );
}

export default RegionList;

const StyledRegion = styled.div<{ imgUrl: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: relative;

  min-width: 64px;
  min-height: 64px;

  border-radius: 100%;
  background-image: ${({ imgUrl }) => `url(${imgUrl})`};
  background-size: cover;

  overflow: hidden;

  cursor: pointer;
`;

const StyledMask = styled.div`
  position: absolute;

  width: 100%;
  height: 100%;

  background-color: black;

  opacity: 0.35;
`;

const StyledRegionName = styled.span`
  z-index: 1;

  color: var(--white);
  font-size: ${FONT_SIZE.sm};
  line-height: 12px;
  text-align: center;
`;
