<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('lapin5App.lapin.home.title')" id="lapin-heading">Lapins</span>
            <router-link :to="{name: 'LapinCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-lapin">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('lapin5App.lapin.home.createLabel')">
                    Create a new Lapin
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && lapins && lapins.length === 0">
            <span v-text="$t('lapin5App.lapin.home.notFound')">No lapins found</span>
        </div>
        <div class="table-responsive" v-if="lapins && lapins.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('lapin5App.lapin.created')">Created</span></th>
                    <th><span v-text="$t('lapin5App.lapin.modify')">Modify</span></th>
                    <th><span v-text="$t('lapin5App.lapin.deleted')">Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="lapin in lapins"
                    :key="lapin.id">
                    <td>
                        <router-link :to="{name: 'LapinView', params: {lapinId: lapin.id}}">{{lapin.id}}</router-link>
                    </td>
                    <td>{{lapin.created}}</td>
                    <td>{{lapin.modify ? $d(Date.parse(lapin.modify), 'short') : ''}}</td>
                    <td>{{lapin.deleted ? $d(Date.parse(lapin.deleted), 'short') : ''}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'LapinView', params: {lapinId: lapin.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'LapinEdit', params: {lapinId: lapin.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(lapin)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="lapin5App.lapin.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-lapin-heading" v-text="$t('lapin5App.lapin.delete.question', {'id': removeId})">Are you sure you want to delete this Lapin?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-lapin" v-text="$t('entity.action.delete')" v-on:click="removeLapin()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./lapin.component.ts">
</script>
