import type { Meta, StoryObj } from '@storybook/react';
import Map from './Map';

const meta: Meta<typeof Map> = {
  title: 'Map',
  component: Map,
};

export default meta;

type Story = StoryObj<typeof Map>;

export const AddressBasedMap: Story = {
  args: {
    width: '1000px',
    height: '400px',
    searchWord: { address: '서울 마포구 양화로 78-7 1층 카와카츠' },
  },
};

export const CoordinateBasedMap: Story = {
  args: {
    width: '1000px',
    height: '400px',
    searchWord: { latitude: 127.050727, longitude: 37.5057482 },
  },
};
