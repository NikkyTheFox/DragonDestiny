import { NotificationEnum } from './notification-enum';

export interface NotificationMessage{
    notificationOption: NotificationEnum;
    name: string;
    number: number;
    bool: boolean;
}