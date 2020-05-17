import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILapin } from '@/shared/model/lapin.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import LapinService from './lapin.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Lapin extends mixins(AlertMixin) {
  @Inject('lapinService') private lapinService: () => LapinService;
  private removeId: number = null;

  public lapins: ILapin[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLapins();
  }

  public clear(): void {
    this.retrieveAllLapins();
  }

  public retrieveAllLapins(): void {
    this.isFetching = true;

    this.lapinService()
      .retrieve()
      .then(
        res => {
          this.lapins = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ILapin): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLapin(): void {
    this.lapinService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('lapin5App.lapin.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllLapins();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
