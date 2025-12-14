export class Brand {

  id!: number;
  name!: string;
  taxIdentificationNumber!: string;

  constructor(id: number, name: string, taxIdentificationNumber: string) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
  }
}
