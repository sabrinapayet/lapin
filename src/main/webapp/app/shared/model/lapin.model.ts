export interface ILapin {
  id?: number;
  created?: Date;
  modify?: Date;
  deleted?: Date;
}

export class Lapin implements ILapin {
  constructor(public id?: number, public created?: Date, public modify?: Date, public deleted?: Date) {}
}
