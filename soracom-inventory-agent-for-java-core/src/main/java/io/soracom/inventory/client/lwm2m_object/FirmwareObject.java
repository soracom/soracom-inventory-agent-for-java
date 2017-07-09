package io.soracom.inventory.client.lwm2m_object;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmwareObject extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(FirmwareObject.class);

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read value for Firmware " + resourceid);
        switch (resourceid) {
        case 5:// Update Result
            return ReadResponse.success(resourceid, 1);
        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Write value for Firmware " + resourceid);
        return super.write(resourceid, value);
    }

    @Override
    public ExecuteResponse execute(int resourceid, String params) {
        LOG.info("Execute command for Firmware " + resourceid + " params:" + params);
        return super.execute(resourceid, params);
    }

}