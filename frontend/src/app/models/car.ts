import { Accessory } from './accessory';
import { Brand } from './brand';

export class Car {
  id?: number;
  name: string = '';
  model: string = '';
  manufactureYear: number = new Date().getFullYear();
  brand?: Brand;
  accessories!: Accessory[];

  constructor(init?: Partial<Car>) {
    Object.assign(this, init);
  }
}
