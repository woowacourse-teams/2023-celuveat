import { Wrapper } from '@googlemaps/react-wrapper';
import type { Meta, StoryObj } from '@storybook/react';
import Map from './Map';

const meta: Meta<typeof Map> = {
  title: 'Map',
  component: Map,
  decorators: [
    Story => (
      <Wrapper apiKey={process.env.GOOGLE_MAP_API_KEY} language="ko">
        <Story />
      </Wrapper>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof Map>;

export const Default: Story = {
  args: {
    center: { lat: 37.5057482, lng: 127.050727 },
    zoom: 12,
    style: { width: '600px', height: '600px' },
    onIdle: () => {},
    onClick: () => {},
  },
};
