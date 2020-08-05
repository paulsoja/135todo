package com.paulsoia.todo135.data.mapper

import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.data.database.entity.TagEntity
import global.zakaz.stockman.data.mapper.base.Mapper

class TagMapper : Mapper<TagEntity, Tag>() {

    override fun reverse(to: Tag): TagEntity {
        return with(to) { TagEntity(id, tag, count) }
    }

    override fun map(from: TagEntity): Tag {
        return with(from) { Tag(id, tag, count) }
    }

}