package org.hl7.tinkar.entity;

import io.activej.bytebuf.ByteBuf;
import org.hl7.tinkar.common.id.PublicId;
import org.hl7.tinkar.common.service.PrimitiveData;
import org.hl7.tinkar.common.util.time.DateTimeUtil;
import org.hl7.tinkar.component.FieldDataType;
import org.hl7.tinkar.component.Stamp;
import org.hl7.tinkar.dto.StampDTO;
import org.hl7.tinkar.terms.ConceptFacade;
import org.hl7.tinkar.terms.EntityProxy;

public class StampEntityVersion extends EntityVersion implements Stamp {

    int statusNid;
    long time;
    int authorNid;
    int moduleNid;
    int pathNid;

    @Override
    public FieldDataType dataType() {
        return FieldDataType.STAMP;
    }

    @Override
    protected void finishVersionFill(ByteBuf readBuf, byte formatVersion) {
        statusNid = readBuf.readInt();
        time = readBuf.readLong();
        authorNid = readBuf.readInt();
        moduleNid =  readBuf.readInt();
        pathNid = readBuf.readInt();
    }

    @Override
    protected int subclassFieldBytesSize() {
        return 24;
    }

    @Override
    protected void writeVersionFields(ByteBuf writeBuf) {
        writeBuf.writeInt(statusNid);
        writeBuf.writeLong(time);
        writeBuf.writeInt(authorNid);
        writeBuf.writeInt(moduleNid);
        writeBuf.writeInt(pathNid);
    }

    @Override
    public PublicId publicId() {
        return chronology;
    }

    @Override
    public ConceptFacade state() {
        return EntityProxy.Concept.make(statusNid);
    }

    @Override
    public long time() {
        return time;
    }

    @Override
    public ConceptFacade author() {
        return EntityProxy.Concept.make(authorNid);
    }

    @Override
    public ConceptFacade module() {
        return EntityProxy.Concept.make(moduleNid);
    }

    @Override
    public ConceptFacade path() {
        return EntityProxy.Concept.make(pathNid);
    }

    @Override
    public StampEntity stamp() {
        return (StampEntity) chronology;
    }


    public String describe() {
        return "s:" + PrimitiveData.text(statusNid) +
                " t:" + DateTimeUtil.format(time()) +
                " a:" + PrimitiveData.text(authorNid) +
                " m:" + PrimitiveData.text(moduleNid) +
                " p:" + PrimitiveData.text(pathNid);
    }

    @Override
    public String toString() {
        return "sv{" + describe() + "}";
    }

    public static StampEntityVersion make(StampEntity stampEntity, ByteBuf readBuf, byte formatVersion) {
        StampEntityVersion version = new StampEntityVersion();
        version.fill(stampEntity, readBuf, formatVersion);
        return version;
    }

    public static StampEntityVersion make(StampEntity stampEntity, StampDTO versionToCopy) {
        StampEntityVersion version = new StampEntityVersion();
        version.fill(stampEntity, versionToCopy);
        version.statusNid = PrimitiveData.get().nidForPublicId(versionToCopy.statusPublicId());
        version.time = versionToCopy.time();
        version.authorNid = PrimitiveData.get().nidForPublicId(versionToCopy.authorPublicId());
        version.moduleNid = PrimitiveData.get().nidForPublicId(versionToCopy.modulePublicId());
        version.pathNid = PrimitiveData.get().nidForPublicId(versionToCopy.pathPublicId());
        return version;
    }

}
