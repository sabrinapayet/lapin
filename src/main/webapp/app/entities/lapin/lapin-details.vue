<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="lapin">
                <h2 class="jh-entity-heading"><span v-text="$t('lapin5App.lapin.detail.title')">Lapin</span> {{lapin.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('lapin5App.lapin.created')">Created</span>
                    </dt>
                    <dd>
                        <span>{{lapin.created}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('lapin5App.lapin.modify')">Modify</span>
                    </dt>
                    <dd>
                        <span v-if="lapin.modify">{{$d(Date.parse(lapin.modify), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('lapin5App.lapin.deleted')">Deleted</span>
                    </dt>
                    <dd>
                        <span v-if="lapin.deleted">{{$d(Date.parse(lapin.deleted), 'long') }}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="lapin.id" :to="{name: 'LapinEdit', params: {lapinId: lapin.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./lapin-details.component.ts">
</script>
