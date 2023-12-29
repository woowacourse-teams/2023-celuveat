import NavItem from '~/components/@common/NavItem';
import ProfileImage from '~/components/@common/ProfileImage';
import CelebIcon from '~/assets/icons/celeb.svg';

import { Celeb } from '~/@types/celeb.types';

interface CelebNavItemProps {
  celeb: Celeb;
}

function CelebNavItem({ celeb }: CelebNavItemProps) {
  return (
    <div>
      {celeb.id === -1 ? (
        <NavItem label="전체 셀럽" icon={<CelebIcon />} />
      ) : (
        <NavItem
          label={celeb.youtubeChannelName.replace('@', '')}
          icon={<ProfileImage name={celeb.name} imageUrl={celeb.profileImageUrl} size="40px" />}
        />
      )}
    </div>
  );
}

export default CelebNavItem;
