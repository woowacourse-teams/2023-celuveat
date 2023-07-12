import type { Meta, StoryObj } from '@storybook/react';
import Map from './Map';

const meta: Meta<typeof Map> = {
  title: 'Map',
  component: Map,
};

export default meta;

type Story = StoryObj<typeof Map>;

export const Default: Story = {
  args: {
    width: '500px',
    height: '300px',
    level: 3,
    mainPosition: { latitude: 127.050727, longitude: 37.5057482 },
    markers: [{ latitude: 127.050727, longitude: 37.5057482 }],
  },
};

export const MapWithManyMarkers: Story = {
  args: {
    width: '1000px',
    height: '400px',
    level: 6,
    mainPosition: { latitude: 127.050727, longitude: 37.5057482 },
    markers: [
      { latitude: 127.023432, longitude: 37.5043233 },
      { latitude: 127.050727, longitude: 37.5057482 },
      { latitude: 127.034234, longitude: 37.5023415 },
      { latitude: 127.062341, longitude: 37.5123423 },
    ],
  },
};
