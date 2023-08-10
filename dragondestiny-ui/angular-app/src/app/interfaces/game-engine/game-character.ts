import {Field} from "./field";

export interface Character{
  id: number;
  name: string;
  profession: string;
  story: string;
  initialStrength: number;
  initialHealth: number;
  field: Field | null;
}
