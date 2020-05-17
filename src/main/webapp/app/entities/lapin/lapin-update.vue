<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <b-form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="lapin5App.lapin.home.createOrEditLabel" v-text="$t('lapin5App.lapin.home.createOrEditLabel')">Create or edit a Lapin</h2>
                <div>
                        <b-form-group
                        v-if="lapin.id"
                        label="ID"
                        label-for="id"
                        v-text="$t('global.field.id')"
                        >
                        <b-form-input id="id" name="id" v-model="lapin.id" readonly></b-form-input>
                        </b-form-group>


                        <b-form-group
                        label="Created"
                        label-for="lapin-created"
                        v-text="$t('lapin5App.lapin.created')"
                        description="$v.lapin.created.$model"
                        invalid-feedback="$v.lapin.created.$invalid"
                        valid-feedback="$v.lapin.created.$invalid"
                        >
                        <b-form-datepicker id="lapin-created" v-model="$v.lapin.created.$model" :start-weekday="1" class="mb-2"></b-form-datepicker>
                        </b-form-group>
                    
                   
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('lapin5App.lapin.modify')" for="lapin-modify">Modify</label>
                        <div class="d-flex">
                            <input id="lapin-modify" type="datetime-local" class="form-control" name="modify" :class="{'valid': !$v.lapin.modify.$invalid, 'invalid': $v.lapin.modify.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.lapin.modify.$model)"
                            @change="updateInstantField('modify', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('lapin5App.lapin.deleted')" for="lapin-deleted">Deleted</label>
                        <div class="d-flex">
                            <input id="lapin-deleted" type="datetime-local" class="form-control" name="deleted" :class="{'valid': !$v.lapin.deleted.$invalid, 'invalid': $v.lapin.deleted.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.lapin.deleted.$model)"
                            @change="updateZonedDateTimeField('deleted', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.lapin.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </b-form>
        </div>
    </div>
</template>
<script lang="ts" src="./lapin-update.component.ts">
</script>
