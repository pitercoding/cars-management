import { Accessory } from './accessory';
import { Brand } from './brand';
import { Owner } from './owner';

export class Car {
  id?: number;
  name: string = '';
  model: string = '';
  manufactureYear: number = new Date().getFullYear();
  brand?: Brand;
  accessories: Accessory[] = [];
  owner?: Owner;

  constructor(init?: Partial<Car>) {
    Object.assign(this, init);
  }
}
