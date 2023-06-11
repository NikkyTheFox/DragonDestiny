import {Field} from "./field";

export interface Board {
  id: number;
  fieldsInBoard: Field[];
  ysize: number;
  xsize: number;
}
